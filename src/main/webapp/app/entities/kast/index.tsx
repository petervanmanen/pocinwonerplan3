import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Kast from './kast';
import KastDetail from './kast-detail';
import KastUpdate from './kast-update';
import KastDeleteDialog from './kast-delete-dialog';

const KastRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Kast />} />
    <Route path="new" element={<KastUpdate />} />
    <Route path=":id">
      <Route index element={<KastDetail />} />
      <Route path="edit" element={<KastUpdate />} />
      <Route path="delete" element={<KastDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default KastRoutes;
