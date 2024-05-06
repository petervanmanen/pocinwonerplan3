import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Fractie from './fractie';
import FractieDetail from './fractie-detail';
import FractieUpdate from './fractie-update';
import FractieDeleteDialog from './fractie-delete-dialog';

const FractieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Fractie />} />
    <Route path="new" element={<FractieUpdate />} />
    <Route path=":id">
      <Route index element={<FractieDetail />} />
      <Route path="edit" element={<FractieUpdate />} />
      <Route path="delete" element={<FractieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default FractieRoutes;
