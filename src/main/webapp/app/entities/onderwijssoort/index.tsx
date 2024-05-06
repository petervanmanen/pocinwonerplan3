import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Onderwijssoort from './onderwijssoort';
import OnderwijssoortDetail from './onderwijssoort-detail';
import OnderwijssoortUpdate from './onderwijssoort-update';
import OnderwijssoortDeleteDialog from './onderwijssoort-delete-dialog';

const OnderwijssoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Onderwijssoort />} />
    <Route path="new" element={<OnderwijssoortUpdate />} />
    <Route path=":id">
      <Route index element={<OnderwijssoortDetail />} />
      <Route path="edit" element={<OnderwijssoortUpdate />} />
      <Route path="delete" element={<OnderwijssoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OnderwijssoortRoutes;
