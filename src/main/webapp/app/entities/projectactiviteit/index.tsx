import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projectactiviteit from './projectactiviteit';
import ProjectactiviteitDetail from './projectactiviteit-detail';
import ProjectactiviteitUpdate from './projectactiviteit-update';
import ProjectactiviteitDeleteDialog from './projectactiviteit-delete-dialog';

const ProjectactiviteitRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Projectactiviteit />} />
    <Route path="new" element={<ProjectactiviteitUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectactiviteitDetail />} />
      <Route path="edit" element={<ProjectactiviteitUpdate />} />
      <Route path="delete" element={<ProjectactiviteitDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectactiviteitRoutes;
