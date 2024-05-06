import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Idealisatie from './idealisatie';
import IdealisatieDetail from './idealisatie-detail';
import IdealisatieUpdate from './idealisatie-update';
import IdealisatieDeleteDialog from './idealisatie-delete-dialog';

const IdealisatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Idealisatie />} />
    <Route path="new" element={<IdealisatieUpdate />} />
    <Route path=":id">
      <Route index element={<IdealisatieDetail />} />
      <Route path="edit" element={<IdealisatieUpdate />} />
      <Route path="delete" element={<IdealisatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default IdealisatieRoutes;
