import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Trajectactiviteitsoort from './trajectactiviteitsoort';
import TrajectactiviteitsoortDetail from './trajectactiviteitsoort-detail';
import TrajectactiviteitsoortUpdate from './trajectactiviteitsoort-update';
import TrajectactiviteitsoortDeleteDialog from './trajectactiviteitsoort-delete-dialog';

const TrajectactiviteitsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Trajectactiviteitsoort />} />
    <Route path="new" element={<TrajectactiviteitsoortUpdate />} />
    <Route path=":id">
      <Route index element={<TrajectactiviteitsoortDetail />} />
      <Route path="edit" element={<TrajectactiviteitsoortUpdate />} />
      <Route path="delete" element={<TrajectactiviteitsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrajectactiviteitsoortRoutes;
