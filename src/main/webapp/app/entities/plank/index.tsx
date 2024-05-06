import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Plank from './plank';
import PlankDetail from './plank-detail';
import PlankUpdate from './plank-update';
import PlankDeleteDialog from './plank-delete-dialog';

const PlankRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Plank />} />
    <Route path="new" element={<PlankUpdate />} />
    <Route path=":id">
      <Route index element={<PlankDetail />} />
      <Route path="edit" element={<PlankUpdate />} />
      <Route path="delete" element={<PlankDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PlankRoutes;
