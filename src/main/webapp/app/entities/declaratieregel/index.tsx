import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Declaratieregel from './declaratieregel';
import DeclaratieregelDetail from './declaratieregel-detail';
import DeclaratieregelUpdate from './declaratieregel-update';
import DeclaratieregelDeleteDialog from './declaratieregel-delete-dialog';

const DeclaratieregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Declaratieregel />} />
    <Route path="new" element={<DeclaratieregelUpdate />} />
    <Route path=":id">
      <Route index element={<DeclaratieregelDetail />} />
      <Route path="edit" element={<DeclaratieregelUpdate />} />
      <Route path="delete" element={<DeclaratieregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DeclaratieregelRoutes;
