import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { ASC } from 'app/shared/util/pagination.constants';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IAfwijkendcorrespondentiepostadresrol, defaultValue } from 'app/shared/model/afwijkendcorrespondentiepostadresrol.model';

const initialState: EntityState<IAfwijkendcorrespondentiepostadresrol> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/afwijkendcorrespondentiepostadresrols';

// Actions

export const getEntities = createAsyncThunk('afwijkendcorrespondentiepostadresrol/fetch_entity_list', async ({ sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<IAfwijkendcorrespondentiepostadresrol[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'afwijkendcorrespondentiepostadresrol/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IAfwijkendcorrespondentiepostadresrol>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'afwijkendcorrespondentiepostadresrol/create_entity',
  async (entity: IAfwijkendcorrespondentiepostadresrol, thunkAPI) => {
    const result = await axios.post<IAfwijkendcorrespondentiepostadresrol>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'afwijkendcorrespondentiepostadresrol/update_entity',
  async (entity: IAfwijkendcorrespondentiepostadresrol, thunkAPI) => {
    const result = await axios.put<IAfwijkendcorrespondentiepostadresrol>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'afwijkendcorrespondentiepostadresrol/partial_update_entity',
  async (entity: IAfwijkendcorrespondentiepostadresrol, thunkAPI) => {
    const result = await axios.patch<IAfwijkendcorrespondentiepostadresrol>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'afwijkendcorrespondentiepostadresrol/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IAfwijkendcorrespondentiepostadresrol>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const AfwijkendcorrespondentiepostadresrolSlice = createEntitySlice({
  name: 'afwijkendcorrespondentiepostadresrol',
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

export const { reset } = AfwijkendcorrespondentiepostadresrolSlice.actions;

// Reducer
export default AfwijkendcorrespondentiepostadresrolSlice.reducer;
