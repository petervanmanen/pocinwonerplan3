import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projectlocatie from './projectlocatie';
import ProjectlocatieDetail from './projectlocatie-detail';
import ProjectlocatieUpdate from './projectlocatie-update';
import ProjectlocatieDeleteDialog from './projectlocatie-delete-dialog';

const ProjectlocatieRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Projectlocatie />} />
    <Route path="new" element={<ProjectlocatieUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectlocatieDetail />} />
      <Route path="edit" element={<ProjectlocatieUpdate />} />
      <Route path="delete" element={<ProjectlocatieDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectlocatieRoutes;
