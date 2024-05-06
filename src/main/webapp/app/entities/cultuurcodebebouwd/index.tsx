import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Cultuurcodebebouwd from './cultuurcodebebouwd';
import CultuurcodebebouwdDetail from './cultuurcodebebouwd-detail';
import CultuurcodebebouwdUpdate from './cultuurcodebebouwd-update';
import CultuurcodebebouwdDeleteDialog from './cultuurcodebebouwd-delete-dialog';

const CultuurcodebebouwdRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Cultuurcodebebouwd />} />
    <Route path="new" element={<CultuurcodebebouwdUpdate />} />
    <Route path=":id">
      <Route index element={<CultuurcodebebouwdDetail />} />
      <Route path="edit" element={<CultuurcodebebouwdUpdate />} />
      <Route path="delete" element={<CultuurcodebebouwdDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default CultuurcodebebouwdRoutes;
