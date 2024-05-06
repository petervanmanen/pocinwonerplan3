import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Programma from './programma';
import ProgrammaDetail from './programma-detail';
import ProgrammaUpdate from './programma-update';
import ProgrammaDeleteDialog from './programma-delete-dialog';

const ProgrammaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Programma />} />
    <Route path="new" element={<ProgrammaUpdate />} />
    <Route path=":id">
      <Route index element={<ProgrammaDetail />} />
      <Route path="edit" element={<ProgrammaUpdate />} />
      <Route path="delete" element={<ProgrammaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProgrammaRoutes;
