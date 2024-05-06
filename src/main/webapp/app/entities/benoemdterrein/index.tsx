import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Benoemdterrein from './benoemdterrein';
import BenoemdterreinDetail from './benoemdterrein-detail';
import BenoemdterreinUpdate from './benoemdterrein-update';
import BenoemdterreinDeleteDialog from './benoemdterrein-delete-dialog';

const BenoemdterreinRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Benoemdterrein />} />
    <Route path="new" element={<BenoemdterreinUpdate />} />
    <Route path=":id">
      <Route index element={<BenoemdterreinDetail />} />
      <Route path="edit" element={<BenoemdterreinUpdate />} />
      <Route path="delete" element={<BenoemdterreinDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BenoemdterreinRoutes;
