import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Fietsparkeervoorziening from './fietsparkeervoorziening';
import FietsparkeervoorzieningDetail from './fietsparkeervoorziening-detail';
import FietsparkeervoorzieningUpdate from './fietsparkeervoorziening-update';
import FietsparkeervoorzieningDeleteDialog from './fietsparkeervoorziening-delete-dialog';

const FietsparkeervoorzieningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Fietsparkeervoorziening />} />
    <Route path="new" element={<FietsparkeervoorzieningUpdate />} />
    <Route path=":id">
      <Route index element={<FietsparkeervoorzieningDetail />} />
      <Route path="edit" element={<FietsparkeervoorzieningUpdate />} />
      <Route path="delete" element={<FietsparkeervoorzieningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FietsparkeervoorzieningRoutes;
