import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Z from './z';
import ZDetail from './z-detail';
import ZUpdate from './z-update';
import ZDeleteDialog from './z-delete-dialog';

const ZRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Z />} />
    <Route path="new" element={<ZUpdate />} />
    <Route path=":id">
      <Route index element={<ZDetail />} />
      <Route path="edit" element={<ZUpdate />} />
      <Route path="delete" element={<ZDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ZRoutes;
