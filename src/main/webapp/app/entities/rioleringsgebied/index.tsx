import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Rioleringsgebied from './rioleringsgebied';
import RioleringsgebiedDetail from './rioleringsgebied-detail';
import RioleringsgebiedUpdate from './rioleringsgebied-update';
import RioleringsgebiedDeleteDialog from './rioleringsgebied-delete-dialog';

const RioleringsgebiedRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Rioleringsgebied />} />
    <Route path="new" element={<RioleringsgebiedUpdate />} />
    <Route path=":id">
      <Route index element={<RioleringsgebiedDetail />} />
      <Route path="edit" element={<RioleringsgebiedUpdate />} />
      <Route path="delete" element={<RioleringsgebiedDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default RioleringsgebiedRoutes;
