import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Hoofdrekening from './hoofdrekening';
import HoofdrekeningDetail from './hoofdrekening-detail';
import HoofdrekeningUpdate from './hoofdrekening-update';
import HoofdrekeningDeleteDialog from './hoofdrekening-delete-dialog';

const HoofdrekeningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Hoofdrekening />} />
    <Route path="new" element={<HoofdrekeningUpdate />} />
    <Route path=":id">
      <Route index element={<HoofdrekeningDetail />} />
      <Route path="edit" element={<HoofdrekeningUpdate />} />
      <Route path="delete" element={<HoofdrekeningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HoofdrekeningRoutes;
