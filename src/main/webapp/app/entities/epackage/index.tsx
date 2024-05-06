import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Epackage from './epackage';
import EpackageDetail from './epackage-detail';
import EpackageUpdate from './epackage-update';
import EpackageDeleteDialog from './epackage-delete-dialog';

const EpackageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Epackage />} />
    <Route path="new" element={<EpackageUpdate />} />
    <Route path=":id">
      <Route index element={<EpackageDetail />} />
      <Route path="edit" element={<EpackageUpdate />} />
      <Route path="delete" element={<EpackageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default EpackageRoutes;
