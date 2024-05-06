import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projectsoort from './projectsoort';
import ProjectsoortDetail from './projectsoort-detail';
import ProjectsoortUpdate from './projectsoort-update';
import ProjectsoortDeleteDialog from './projectsoort-delete-dialog';

const ProjectsoortRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Projectsoort />} />
    <Route path="new" element={<ProjectsoortUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectsoortDetail />} />
      <Route path="edit" element={<ProjectsoortUpdate />} />
      <Route path="delete" element={<ProjectsoortDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectsoortRoutes;
