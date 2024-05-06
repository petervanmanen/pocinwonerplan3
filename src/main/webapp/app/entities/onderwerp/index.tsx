import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderwerp from './onderwerp';
import OnderwerpDetail from './onderwerp-detail';
import OnderwerpUpdate from './onderwerp-update';
import OnderwerpDeleteDialog from './onderwerp-delete-dialog';

const OnderwerpRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderwerp />} />
    <Route path="new" element={<OnderwerpUpdate />} />
    <Route path=":id">
      <Route index element={<OnderwerpDetail />} />
      <Route path="edit" element={<OnderwerpUpdate />} />
      <Route path="delete" element={<OnderwerpDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderwerpRoutes;
