import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Archeologiebesluit from './archeologiebesluit';
import ArcheologiebesluitDetail from './archeologiebesluit-detail';
import ArcheologiebesluitUpdate from './archeologiebesluit-update';
import ArcheologiebesluitDeleteDialog from './archeologiebesluit-delete-dialog';

const ArcheologiebesluitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Archeologiebesluit />} />
    <Route path="new" element={<ArcheologiebesluitUpdate />} />
    <Route path=":id">
      <Route index element={<ArcheologiebesluitDetail />} />
      <Route path="edit" element={<ArcheologiebesluitUpdate />} />
      <Route path="delete" element={<ArcheologiebesluitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ArcheologiebesluitRoutes;
