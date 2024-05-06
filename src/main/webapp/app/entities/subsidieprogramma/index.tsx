import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Subsidieprogramma from './subsidieprogramma';
import SubsidieprogrammaDetail from './subsidieprogramma-detail';
import SubsidieprogrammaUpdate from './subsidieprogramma-update';
import SubsidieprogrammaDeleteDialog from './subsidieprogramma-delete-dialog';

const SubsidieprogrammaRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Subsidieprogramma />} />
    <Route path="new" element={<SubsidieprogrammaUpdate />} />
    <Route path=":id">
      <Route index element={<SubsidieprogrammaDetail />} />
      <Route path="edit" element={<SubsidieprogrammaUpdate />} />
      <Route path="delete" element={<SubsidieprogrammaDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default SubsidieprogrammaRoutes;
