import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Selectietabelaanbesteding from './selectietabelaanbesteding';
import SelectietabelaanbestedingDetail from './selectietabelaanbesteding-detail';
import SelectietabelaanbestedingUpdate from './selectietabelaanbesteding-update';
import SelectietabelaanbestedingDeleteDialog from './selectietabelaanbesteding-delete-dialog';

const SelectietabelaanbestedingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Selectietabelaanbesteding />} />
    <Route path="new" element={<SelectietabelaanbestedingUpdate />} />
    <Route path=":id">
      <Route index element={<SelectietabelaanbestedingDetail />} />
      <Route path="edit" element={<SelectietabelaanbestedingUpdate />} />
      <Route path="delete" element={<SelectietabelaanbestedingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SelectietabelaanbestedingRoutes;
