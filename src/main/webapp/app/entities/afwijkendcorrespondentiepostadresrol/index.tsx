import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Afwijkendcorrespondentiepostadresrol from './afwijkendcorrespondentiepostadresrol';
import AfwijkendcorrespondentiepostadresrolDetail from './afwijkendcorrespondentiepostadresrol-detail';
import AfwijkendcorrespondentiepostadresrolUpdate from './afwijkendcorrespondentiepostadresrol-update';
import AfwijkendcorrespondentiepostadresrolDeleteDialog from './afwijkendcorrespondentiepostadresrol-delete-dialog';

const AfwijkendcorrespondentiepostadresrolRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Afwijkendcorrespondentiepostadresrol />} />
    <Route path="new" element={<AfwijkendcorrespondentiepostadresrolUpdate />} />
    <Route path=":id">
      <Route index element={<AfwijkendcorrespondentiepostadresrolDetail />} />
      <Route path="edit" element={<AfwijkendcorrespondentiepostadresrolUpdate />} />
      <Route path="delete" element={<AfwijkendcorrespondentiepostadresrolDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default AfwijkendcorrespondentiepostadresrolRoutes;
