import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Parkeergarage from './parkeergarage';
import ParkeergarageDetail from './parkeergarage-detail';
import ParkeergarageUpdate from './parkeergarage-update';
import ParkeergarageDeleteDialog from './parkeergarage-delete-dialog';

const ParkeergarageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Parkeergarage />} />
    <Route path="new" element={<ParkeergarageUpdate />} />
    <Route path=":id">
      <Route index element={<ParkeergarageDetail />} />
      <Route path="edit" element={<ParkeergarageUpdate />} />
      <Route path="delete" element={<ParkeergarageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParkeergarageRoutes;
