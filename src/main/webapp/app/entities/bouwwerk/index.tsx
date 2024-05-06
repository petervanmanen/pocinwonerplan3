import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Bouwwerk from './bouwwerk';
import BouwwerkDetail from './bouwwerk-detail';
import BouwwerkUpdate from './bouwwerk-update';
import BouwwerkDeleteDialog from './bouwwerk-delete-dialog';

const BouwwerkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Bouwwerk />} />
    <Route path="new" element={<BouwwerkUpdate />} />
    <Route path=":id">
      <Route index element={<BouwwerkDetail />} />
      <Route path="edit" element={<BouwwerkUpdate />} />
      <Route path="delete" element={<BouwwerkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default BouwwerkRoutes;
