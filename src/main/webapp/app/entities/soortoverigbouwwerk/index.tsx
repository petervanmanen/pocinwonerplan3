import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Soortoverigbouwwerk from './soortoverigbouwwerk';
import SoortoverigbouwwerkDetail from './soortoverigbouwwerk-detail';
import SoortoverigbouwwerkUpdate from './soortoverigbouwwerk-update';
import SoortoverigbouwwerkDeleteDialog from './soortoverigbouwwerk-delete-dialog';

const SoortoverigbouwwerkRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Soortoverigbouwwerk />} />
    <Route path="new" element={<SoortoverigbouwwerkUpdate />} />
    <Route path=":id">
      <Route index element={<SoortoverigbouwwerkDetail />} />
      <Route path="edit" element={<SoortoverigbouwwerkUpdate />} />
      <Route path="delete" element={<SoortoverigbouwwerkDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SoortoverigbouwwerkRoutes;
