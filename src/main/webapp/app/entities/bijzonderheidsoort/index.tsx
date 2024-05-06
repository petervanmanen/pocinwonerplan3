import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bijzonderheidsoort from './bijzonderheidsoort';
import BijzonderheidsoortDetail from './bijzonderheidsoort-detail';
import BijzonderheidsoortUpdate from './bijzonderheidsoort-update';
import BijzonderheidsoortDeleteDialog from './bijzonderheidsoort-delete-dialog';

const BijzonderheidsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bijzonderheidsoort />} />
    <Route path="new" element={<BijzonderheidsoortUpdate />} />
    <Route path=":id">
      <Route index element={<BijzonderheidsoortDetail />} />
      <Route path="edit" element={<BijzonderheidsoortUpdate />} />
      <Route path="delete" element={<BijzonderheidsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BijzonderheidsoortRoutes;
