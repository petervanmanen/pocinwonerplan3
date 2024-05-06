import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Zaal from './zaal';
import ZaalDetail from './zaal-detail';
import ZaalUpdate from './zaal-update';
import ZaalDeleteDialog from './zaal-delete-dialog';

const ZaalRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Zaal />} />
    <Route path="new" element={<ZaalUpdate />} />
    <Route path=":id">
      <Route index element={<ZaalDetail />} />
      <Route path="edit" element={<ZaalUpdate />} />
      <Route path="delete" element={<ZaalDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZaalRoutes;
