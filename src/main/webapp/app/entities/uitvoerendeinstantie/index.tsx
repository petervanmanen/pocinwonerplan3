import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Uitvoerendeinstantie from './uitvoerendeinstantie';
import UitvoerendeinstantieDetail from './uitvoerendeinstantie-detail';
import UitvoerendeinstantieUpdate from './uitvoerendeinstantie-update';
import UitvoerendeinstantieDeleteDialog from './uitvoerendeinstantie-delete-dialog';

const UitvoerendeinstantieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Uitvoerendeinstantie />} />
    <Route path="new" element={<UitvoerendeinstantieUpdate />} />
    <Route path=":id">
      <Route index element={<UitvoerendeinstantieDetail />} />
      <Route path="edit" element={<UitvoerendeinstantieUpdate />} />
      <Route path="delete" element={<UitvoerendeinstantieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default UitvoerendeinstantieRoutes;
