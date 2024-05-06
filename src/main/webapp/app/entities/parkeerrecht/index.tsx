import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Parkeerrecht from './parkeerrecht';
import ParkeerrechtDetail from './parkeerrecht-detail';
import ParkeerrechtUpdate from './parkeerrecht-update';
import ParkeerrechtDeleteDialog from './parkeerrecht-delete-dialog';

const ParkeerrechtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Parkeerrecht />} />
    <Route path="new" element={<ParkeerrechtUpdate />} />
    <Route path=":id">
      <Route index element={<ParkeerrechtDetail />} />
      <Route path="edit" element={<ParkeerrechtUpdate />} />
      <Route path="delete" element={<ParkeerrechtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParkeerrechtRoutes;
