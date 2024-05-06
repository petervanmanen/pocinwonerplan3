import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { ASC } from 'app/shared/util/pagination.constants';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import {
  ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap,
  defaultValue,
} from 'app/shared/model/sluitingofaangaanhuwelijkofgeregistreerdpartnerschap.model';

const initialState: EntityState<ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  updating: false,
  updateSuccess: false,
};

const apiUrl = 'api/sluitingofaangaanhuwelijkofgeregistreerdpartnerschaps';

// Actions

export const getEntities = createAsyncThunk(
  'sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/fetch_entity_list',
  async ({ sort }: IQueryParams) => {
    const requestUrl = `${apiUrl}?${sort ? `sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
    return axios.get<ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap[]>(requestUrl);
  },
);

export const getEntity = createAsyncThunk(
  'sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/create_entity',
  async (entity: ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap, thunkAPI) => {
    const result = await axios.post<ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap>(apiUrl, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/update_entity',
  async (entity: ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap, thunkAPI) => {
    const result = await axios.put<ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/partial_update_entity',
  async (entity: ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap, thunkAPI) => {
    const result = await axios.patch<ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'sluitingofaangaanhuwelijkofgeregistreerdpartnerschap/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    const result = await axios.delete<ISluitingofaangaanhuwelijkofgeregistreerdpartnerschap>(requestUrl);
    thunkAPI.dispatch(getEntities({}));
    return result;
  },
  { serializeError: serializeAxiosError },
);

// slice

export const SluitingofaangaanhuwelijkofgeregistreerdpartnerschapSlice = createEntitySlice({
  name: 'sluitingofaangaanhuwelijkofgeregistreerdpartnerschap',
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

export const { reset } = SluitingofaangaanhuwelijkofgeregistreerdpartnerschapSlice.actions;

// Reducer
export default SluitingofaangaanhuwelijkofgeregistreerdpartnerschapSlice.reducer;
