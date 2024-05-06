import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Projectontwikkelaar from './projectontwikkelaar';
import ProjectontwikkelaarDetail from './projectontwikkelaar-detail';
import ProjectontwikkelaarUpdate from './projectontwikkelaar-update';
import ProjectontwikkelaarDeleteDialog from './projectontwikkelaar-delete-dialog';

const ProjectontwikkelaarRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Projectontwikkelaar />} />
    <Route path="new" element={<ProjectontwikkelaarUpdate />} />
    <Route path=":id">
      <Route index element={<ProjectontwikkelaarDetail />} />
      <Route path="edit" element={<ProjectontwikkelaarUpdate />} />
      <Route path="delete" element={<ProjectontwikkelaarDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default ProjectontwikkelaarRoutes;
