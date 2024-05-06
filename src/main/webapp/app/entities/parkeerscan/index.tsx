import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Parkeerscan from './parkeerscan';
import ParkeerscanDetail from './parkeerscan-detail';
import ParkeerscanUpdate from './parkeerscan-update';
import ParkeerscanDeleteDialog from './parkeerscan-delete-dialog';

const ParkeerscanRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Parkeerscan />} />
    <Route path="new" element={<ParkeerscanUpdate />} />
    <Route path=":id">
      <Route index element={<ParkeerscanDetail />} />
      <Route path="edit" element={<ParkeerscanUpdate />} />
      <Route path="delete" element={<ParkeerscanDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParkeerscanRoutes;
