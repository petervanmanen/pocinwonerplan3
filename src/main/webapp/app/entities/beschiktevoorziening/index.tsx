import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Beschiktevoorziening from './beschiktevoorziening';
import BeschiktevoorzieningDetail from './beschiktevoorziening-detail';
import BeschiktevoorzieningUpdate from './beschiktevoorziening-update';
import BeschiktevoorzieningDeleteDialog from './beschiktevoorziening-delete-dialog';

const BeschiktevoorzieningRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Beschiktevoorziening />} />
    <Route path="new" element={<BeschiktevoorzieningUpdate />} />
    <Route path=":id">
      <Route index element={<BeschiktevoorzieningDetail />} />
      <Route path="edit" element={<BeschiktevoorzieningUpdate />} />
      <Route path="delete" element={<BeschiktevoorzieningDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BeschiktevoorzieningRoutes;
