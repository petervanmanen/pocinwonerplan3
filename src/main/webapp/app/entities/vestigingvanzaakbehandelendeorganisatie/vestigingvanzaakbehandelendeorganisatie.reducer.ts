import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { ASC } from 'app/shared/util/pagination.constants';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { IVestigingvanzaakbehandelendeorganisatie, defaultValue } from 'app/shared/model/vestigingvanzaakbehandelendeorganisatie.model';

const initialState: EntityState<IVestigingvanzaakbehandelendeorganisatie> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/vestigingvanzaakbehandelendeorganisaties';

// Actions

export const getEntities = createAsyncThunk('vestigingvanzaakbehandelendeorganisatie/fetch_entity_list', async ({ sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<IVestigingvanzaakbehandelendeorganisatie[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'vestigingvanzaakbehandelendeorganisatie/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<IVestigingvanzaakbehandelendeorganisatie>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'vestigingvanzaakbehandelendeorganisatie/create_entity',
  async (entity: IVestigingvanzaakbehandelendeorganisatie, thunkAPI) => {
    const result = await axios.post<IVestigingvanzaakbehandelendeorganisatie>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'vestigingvanzaakbehandelendeorganisatie/update_entity',
  async (entity: IVestigingvanzaakbehandelendeorganisatie, thunkAPI) => {
    const result = await axios.put<IVestigingvanzaakbehandelendeorganisatie>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'vestigingvanzaakbehandelendeorganisatie/partial_update_entity',
  async (entity: IVestigingvanzaakbehandelendeorganisatie, thunkAPI) => {
    const result = await axios.patch<IVestigingvanzaakbehandelendeorganisatie>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'vestigingvanzaakbehandelendeorganisatie/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<IVestigingvanzaakbehandelendeorganisatie>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const VestigingvanzaakbehandelendeorganisatieSlice = createEntitySlice({
  name: 'vestigingvanzaakbehandelendeorganisatie',
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

export const { reset } = VestigingvanzaakbehandelendeorganisatieSlice.actions;

// Reducer
export default VestigingvanzaakbehandelendeorganisatieSlice.reducer;
