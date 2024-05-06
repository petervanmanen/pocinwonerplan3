import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Elogboek from './elogboek';
import ElogboekDetail from './elogboek-detail';
import ElogboekUpdate from './elogboek-update';
import ElogboekDeleteDialog from './elogboek-delete-dialog';

const ElogboekRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Elogboek />} />
    <Route path="new" element={<ElogboekUpdate />} />
    <Route path=":id">
      <Route index element={<ElogboekDetail />} />
      <Route path="edit" element={<ElogboekUpdate />} />
      <Route path="delete" element={<ElogboekDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ElogboekRoutes;
