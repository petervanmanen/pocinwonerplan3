import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Programmasoort from './programmasoort';
import ProgrammasoortDetail from './programmasoort-detail';
import ProgrammasoortUpdate from './programmasoort-update';
import ProgrammasoortDeleteDialog from './programmasoort-delete-dialog';

const ProgrammasoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Programmasoort />} />
    <Route path="new" element={<ProgrammasoortUpdate />} />
    <Route path=":id">
      <Route index element={<ProgrammasoortDetail />} />
      <Route path="edit" element={<ProgrammasoortUpdate />} />
      <Route path="delete" element={<ProgrammasoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProgrammasoortRoutes;
