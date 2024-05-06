import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { ASC } from 'app/shared/util/pagination.constants';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import {
  IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon,
  defaultValue,
} from 'app/shared/model/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon.model';

const initialState: EntityState<IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoons';

// Actions

export const getEntities = createAsyncThunk(
  'verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/fetch_entity_list',
  async ({ sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
    return axios.get<IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon[]>(requestUrl);
  },
);

export const getEntity = createAsyncThunk(
  'verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/create_entity',
  async (entity: IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon, thunkAPI) => {
    const result = await axios.post<IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/update_entity',
  async (entity: IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon, thunkAPI) => {
    const result = await axios.put<IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon>(
      `${apiUrl}/${entity.id}`,
      cleanEntity(entity),
    );
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/partial_update_entity',
  async (entity: IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon, thunkAPI) => {
    const result = await axios.patch<IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon>(
      `${apiUrl}/${entity.id}`,
      cleanEntity(entity),
    );
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IVerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonSlice = createEntitySlice({
  name: 'verstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoon',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data } = action.payload;

        return {
          ...state,
          loading: false,
          entities: data.sort((a, b) => {
            if (!action.meta?.arg?.sort) {
              return 1;
            }
            const order = action.meta.arg.sort.split(',')[1];
            const predicate = action.meta.arg.sort.split(',')[0];
            return order === ASC ? (a[predicate] < b[predicate] ? -1 : 1) : b[predicate] < a[predicate] ? -1 : 1;
          }),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonSlice.actions;

// Reducer
export default VerstrekkingsbeperkingpartieelingeschrevennatuurlijkpersoonSlice.reducer;
