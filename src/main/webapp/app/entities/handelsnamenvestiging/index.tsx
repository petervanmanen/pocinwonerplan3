import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Handelsnamenvestiging from './handelsnamenvestiging';
import HandelsnamenvestigingDetail from './handelsnamenvestiging-detail';
import HandelsnamenvestigingUpdate from './handelsnamenvestiging-update';
import HandelsnamenvestigingDeleteDialog from './handelsnamenvestiging-delete-dialog';

const HandelsnamenvestigingRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Handelsnamenvestiging />} />
    <Route path="new" element={<HandelsnamenvestigingUpdate />} />
    <Route path=":id">
      <Route index element={<HandelsnamenvestigingDetail />} />
      <Route path="edit" element={<HandelsnamenvestigingUpdate />} />
      <Route path="delete" element={<HandelsnamenvestigingDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default HandelsnamenvestigingRoutes;
