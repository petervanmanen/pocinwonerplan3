import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Appartementsrecht from './appartementsrecht';
import AppartementsrechtDetail from './appartementsrecht-detail';
import AppartementsrechtUpdate from './appartementsrecht-update';
import AppartementsrechtDeleteDialog from './appartementsrecht-delete-dialog';

const AppartementsrechtRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Appartementsrecht />} />
    <Route path="new" element={<AppartementsrechtUpdate />} />
    <Route path=":id">
      <Route index element={<AppartementsrechtDetail />} />
      <Route path="edit" element={<AppartementsrechtUpdate />} />
      <Route path="delete" element={<AppartementsrechtDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AppartementsrechtRoutes;
