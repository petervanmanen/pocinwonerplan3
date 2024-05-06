import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Instructieregel from './instructieregel';
import InstructieregelDetail from './instructieregel-detail';
import InstructieregelUpdate from './instructieregel-update';
import InstructieregelDeleteDialog from './instructieregel-delete-dialog';

const InstructieregelRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Instructieregel />} />
    <Route path="new" element={<InstructieregelUpdate />} />
    <Route path=":id">
      <Route index element={<InstructieregelDetail />} />
      <Route path="edit" element={<InstructieregelUpdate />} />
      <Route path="delete" element={<InstructieregelDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default InstructieregelRoutes;
