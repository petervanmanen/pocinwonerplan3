import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projectleider from './projectleider';
import ProjectleiderDetail from './projectleider-detail';
import ProjectleiderUpdate from './projectleider-update';
import ProjectleiderDeleteDialog from './projectleider-delete-dialog';

const ProjectleiderRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Projectleider />} />
    <Route path="new" element={<ProjectleiderUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectleiderDetail />} />
      <Route path="edit" element={<ProjectleiderUpdate />} />
      <Route path="delete" element={<ProjectleiderDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectleiderRoutes;
