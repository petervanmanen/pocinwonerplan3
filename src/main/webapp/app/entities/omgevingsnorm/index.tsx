import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Omgevingsnorm from './omgevingsnorm';
import OmgevingsnormDetail from './omgevingsnorm-detail';
import OmgevingsnormUpdate from './omgevingsnorm-update';
import OmgevingsnormDeleteDialog from './omgevingsnorm-delete-dialog';

const OmgevingsnormRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Omgevingsnorm />} />
    <Route path="new" element={<OmgevingsnormUpdate />} />
    <Route path=":id">
      <Route index element={<OmgevingsnormDetail />} />
      <Route path="edit" element={<OmgevingsnormUpdate />} />
      <Route path="delete" element={<OmgevingsnormDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OmgevingsnormRoutes;
