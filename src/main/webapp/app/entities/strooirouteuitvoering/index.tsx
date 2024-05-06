import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Strooirouteuitvoering from './strooirouteuitvoering';
import StrooirouteuitvoeringDetail from './strooirouteuitvoering-detail';
import StrooirouteuitvoeringUpdate from './strooirouteuitvoering-update';
import StrooirouteuitvoeringDeleteDialog from './strooirouteuitvoering-delete-dialog';

const StrooirouteuitvoeringRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Strooirouteuitvoering />} />
    <Route path="new" element={<StrooirouteuitvoeringUpdate />} />
    <Route path=":id">
      <Route index element={<StrooirouteuitvoeringDetail />} />
      <Route path="edit" element={<StrooirouteuitvoeringUpdate />} />
      <Route path="delete" element={<StrooirouteuitvoeringDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default StrooirouteuitvoeringRoutes;
