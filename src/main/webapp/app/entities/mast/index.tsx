import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Mast from './mast';
import MastDetail from './mast-detail';
import MastUpdate from './mast-update';
import MastDeleteDialog from './mast-delete-dialog';

const MastRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Mast />} />
    <Route path="new" element={<MastUpdate />} />
    <Route path=":id">
      <Route index element={<MastDetail />} />
      <Route path="edit" element={<MastUpdate />} />
      <Route path="delete" element={<MastDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default MastRoutes;
