import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Parkeervergunning from './parkeervergunning';
import ParkeervergunningDetail from './parkeervergunning-detail';
import ParkeervergunningUpdate from './parkeervergunning-update';
import ParkeervergunningDeleteDialog from './parkeervergunning-delete-dialog';

const ParkeervergunningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Parkeervergunning />} />
    <Route path="new" element={<ParkeervergunningUpdate />} />
    <Route path=":id">
      <Route index element={<ParkeervergunningDetail />} />
      <Route path="edit" element={<ParkeervergunningUpdate />} />
      <Route path="delete" element={<ParkeervergunningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ParkeervergunningRoutes;
