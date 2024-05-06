import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitstroomredensoort from './uitstroomredensoort';
import UitstroomredensoortDetail from './uitstroomredensoort-detail';
import UitstroomredensoortUpdate from './uitstroomredensoort-update';
import UitstroomredensoortDeleteDialog from './uitstroomredensoort-delete-dialog';

const UitstroomredensoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitstroomredensoort />} />
    <Route path="new" element={<UitstroomredensoortUpdate />} />
    <Route path=":id">
      <Route index element={<UitstroomredensoortDetail />} />
      <Route path="edit" element={<UitstroomredensoortUpdate />} />
      <Route path="delete" element={<UitstroomredensoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitstroomredensoortRoutes;
