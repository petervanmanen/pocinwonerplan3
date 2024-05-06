import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bijzonderheid from './bijzonderheid';
import BijzonderheidDetail from './bijzonderheid-detail';
import BijzonderheidUpdate from './bijzonderheid-update';
import BijzonderheidDeleteDialog from './bijzonderheid-delete-dialog';

const BijzonderheidRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bijzonderheid />} />
    <Route path="new" element={<BijzonderheidUpdate />} />
    <Route path=":id">
      <Route index element={<BijzonderheidDetail />} />
      <Route path="edit" element={<BijzonderheidUpdate />} />
      <Route path="delete" element={<BijzonderheidDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BijzonderheidRoutes;
