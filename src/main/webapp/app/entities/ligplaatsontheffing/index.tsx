import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Ligplaatsontheffing from './ligplaatsontheffing';
import LigplaatsontheffingDetail from './ligplaatsontheffing-detail';
import LigplaatsontheffingUpdate from './ligplaatsontheffing-update';
import LigplaatsontheffingDeleteDialog from './ligplaatsontheffing-delete-dialog';

const LigplaatsontheffingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Ligplaatsontheffing />} />
    <Route path="new" element={<LigplaatsontheffingUpdate />} />
    <Route path=":id">
      <Route index element={<LigplaatsontheffingDetail />} />
      <Route path="edit" element={<LigplaatsontheffingUpdate />} />
      <Route path="delete" element={<LigplaatsontheffingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default LigplaatsontheffingRoutes;
