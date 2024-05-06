import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Aardzakelijkrecht from './aardzakelijkrecht';
import AardzakelijkrechtDetail from './aardzakelijkrecht-detail';
import AardzakelijkrechtUpdate from './aardzakelijkrecht-update';
import AardzakelijkrechtDeleteDialog from './aardzakelijkrecht-delete-dialog';

const AardzakelijkrechtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Aardzakelijkrecht />} />
    <Route path="new" element={<AardzakelijkrechtUpdate />} />
    <Route path=":id">
      <Route index element={<AardzakelijkrechtDetail />} />
      <Route path="edit" element={<AardzakelijkrechtUpdate />} />
      <Route path="delete" element={<AardzakelijkrechtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AardzakelijkrechtRoutes;
