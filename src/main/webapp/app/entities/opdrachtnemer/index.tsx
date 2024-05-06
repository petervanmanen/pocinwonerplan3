import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Opdrachtnemer from './opdrachtnemer';
import OpdrachtnemerDetail from './opdrachtnemer-detail';
import OpdrachtnemerUpdate from './opdrachtnemer-update';
import OpdrachtnemerDeleteDialog from './opdrachtnemer-delete-dialog';

const OpdrachtnemerRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Opdrachtnemer />} />
    <Route path="new" element={<OpdrachtnemerUpdate />} />
    <Route path=":id">
      <Route index element={<OpdrachtnemerDetail />} />
      <Route path="edit" element={<OpdrachtnemerUpdate />} />
      <Route path="delete" element={<OpdrachtnemerDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default OpdrachtnemerRoutes;
