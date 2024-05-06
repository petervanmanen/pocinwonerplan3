import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Trajectactiviteit from './trajectactiviteit';
import TrajectactiviteitDetail from './trajectactiviteit-detail';
import TrajectactiviteitUpdate from './trajectactiviteit-update';
import TrajectactiviteitDeleteDialog from './trajectactiviteit-delete-dialog';

const TrajectactiviteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Trajectactiviteit />} />
    <Route path="new" element={<TrajectactiviteitUpdate />} />
    <Route path=":id">
      <Route index element={<TrajectactiviteitDetail />} />
      <Route path="edit" element={<TrajectactiviteitUpdate />} />
      <Route path="delete" element={<TrajectactiviteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrajectactiviteitRoutes;
