import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Meldingeigenbijdrage from './meldingeigenbijdrage';
import MeldingeigenbijdrageDetail from './meldingeigenbijdrage-detail';
import MeldingeigenbijdrageUpdate from './meldingeigenbijdrage-update';
import MeldingeigenbijdrageDeleteDialog from './meldingeigenbijdrage-delete-dialog';

const MeldingeigenbijdrageRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Meldingeigenbijdrage />} />
    <Route path="new" element={<MeldingeigenbijdrageUpdate />} />
    <Route path=":id">
      <Route index element={<MeldingeigenbijdrageDetail />} />
      <Route path="edit" element={<MeldingeigenbijdrageUpdate />} />
      <Route path="delete" element={<MeldingeigenbijdrageDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MeldingeigenbijdrageRoutes;
