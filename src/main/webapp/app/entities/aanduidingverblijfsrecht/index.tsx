import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aanduidingverblijfsrecht from './aanduidingverblijfsrecht';
import AanduidingverblijfsrechtDetail from './aanduidingverblijfsrecht-detail';
import AanduidingverblijfsrechtUpdate from './aanduidingverblijfsrecht-update';
import AanduidingverblijfsrechtDeleteDialog from './aanduidingverblijfsrecht-delete-dialog';

const AanduidingverblijfsrechtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aanduidingverblijfsrecht />} />
    <Route path="new" element={<AanduidingverblijfsrechtUpdate />} />
    <Route path=":id">
      <Route index element={<AanduidingverblijfsrechtDetail />} />
      <Route path="edit" element={<AanduidingverblijfsrechtUpdate />} />
      <Route path="delete" element={<AanduidingverblijfsrechtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AanduidingverblijfsrechtRoutes;
