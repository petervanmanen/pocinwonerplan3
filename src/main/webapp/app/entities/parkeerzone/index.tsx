import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Parkeerzone from './parkeerzone';
import ParkeerzoneDetail from './parkeerzone-detail';
import ParkeerzoneUpdate from './parkeerzone-update';
import ParkeerzoneDeleteDialog from './parkeerzone-delete-dialog';

const ParkeerzoneRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Parkeerzone />} />
    <Route path="new" element={<ParkeerzoneUpdate />} />
    <Route path=":id">
      <Route index element={<ParkeerzoneDetail />} />
      <Route path="edit" element={<ParkeerzoneUpdate />} />
      <Route path="delete" element={<ParkeerzoneDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParkeerzoneRoutes;
