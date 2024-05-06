import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Trajectsoort from './trajectsoort';
import TrajectsoortDetail from './trajectsoort-detail';
import TrajectsoortUpdate from './trajectsoort-update';
import TrajectsoortDeleteDialog from './trajectsoort-delete-dialog';

const TrajectsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Trajectsoort />} />
    <Route path="new" element={<TrajectsoortUpdate />} />
    <Route path=":id">
      <Route index element={<TrajectsoortDetail />} />
      <Route path="edit" element={<TrajectsoortUpdate />} />
      <Route path="delete" element={<TrajectsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default TrajectsoortRoutes;
